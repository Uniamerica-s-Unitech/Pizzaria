import { Component ,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from 'src/app/models/pedido';
import { Cliente } from 'src/app/models/cliente';
import { Produto } from 'src/app/models/produto';
import { Mensagem } from 'src/app/models/mensagem';
import { PedidoService } from 'src/app/services/pedido.service';
import { PedidoProduto } from 'src/app/models/pedido-produto';

@Component({
  selector: 'app-pedido-lista',
  templateUrl: './pedido-lista.component.html',
  styleUrls: ['./pedido-lista.component.scss']
})
export class PedidoListaComponent {
  @Input() pedido:Pedido = new Pedido();
  @Output() retorno = new EventEmitter<Mensagem>();

  listaPedidosOrginal: Pedido[] = [];
  listaPedidosFiltrada: Pedido[] = [];

  modalService = inject(NgbModal);
  pedidoService = inject(PedidoService);

  pedidoParaEditar: Pedido = new Pedido();
  produtoParaEditar: PedidoProduto = new PedidoProduto();
  ClienteParaEditar: Cliente = new Cliente();
  
  modalRef!: NgbModalRef;
  tituloModal!: string;
  termoPesquisa!: "";
  active!: any;
  indiceSelecionadoParaEdicao!: number;
  pedidoSelecionado: number | null = null;

  constructor() {
    this.listarPedidos();
  }

  mostrarDetalhes(pedido: number) {
    if (this.pedidoSelecionado === pedido) {
      this.pedidoSelecionado = null;
    } else {
      this.pedidoSelecionado = pedido;
    }
  }

  listarPedidos(){
    this.pedidoService.listarAbertos().subscribe({
      next: listaPedidos => {
        this.listaPedidosOrginal = listaPedidos;
        this.listaPedidosFiltrada = listaPedidos;
      }
    })
  }

  cadastrarPedido(modalPedido : any){
    this.pedidoParaEditar = new Pedido();
    this.modalRef = this.modalService.open(modalPedido, { size: 'md' });
    
    this.tituloModal = "Cadastrar Pedido";
  }

  historico(modalPedidoHistorico : any){
    this.modalRef = this.modalService.open(modalPedidoHistorico, { size: 'lg' });
    
    this.tituloModal = "Histórico Pedido";
  }

  atualizarLista(mensagem: Mensagem) {
    this.modalService.dismissAll();
    this.listarPedidos();
    
  }
  finalizarPedido(pedido: Pedido) {
    const dataAtual = new Date();
    pedido.finalizacao = dataAtual;
  
    this.pedidoService.save(pedido).subscribe({
      next: mensagem => {
        this.listarPedidos();
        this.retorno.emit(mensagem);
        // Atualize a lista de pedidos após a finalização bem-sucedida, se necessário.
      },
      error: erro => {
      }
    });
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaPedidosFiltrada = this.listaPedidosOrginal;
    } else {
      this.listaPedidosFiltrada = this.listaPedidosOrginal.filter((pedido: Pedido) => {
        const id = pedido.id.toString().toLowerCase();
        const nome = pedido.clienteId.nome.toLowerCase();
        return (
          id.includes(termoPesquisa) ||
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}
