import { Component ,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from 'src/app/models/pedido';
import { Cliente } from 'src/app/models/cliente';
import { Produto } from 'src/app/models/produto';
import { Mensagem } from 'src/app/models/mensagem';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-pedido-lista',
  templateUrl: './pedido-lista.component.html',
  styleUrls: ['./pedido-lista.component.scss']
})
export class PedidoListaComponent {
  listaPedidos: Pedido[] = [];
  @Input() pedido:Pedido = new Pedido();
  @Output() retorno = new EventEmitter<Mensagem>();

  pedidoParaEditar: Pedido = new Pedido();
  produtoParaEditar: Produto = new Produto();
  ClienteParaEditar: Cliente = new Cliente();
  indiceSelecionadoParaEdicao!: number;
  pedidoSelecionado: number | null = null;
  
  modalService = inject(NgbModal);
  pedidoService = inject(PedidoService);


  constructor() {
    this.listar();
  }

  mostrarDetalhes(pedido: number) {
    if (this.pedidoSelecionado === pedido) {
      this.pedidoSelecionado = null;
    } else {
      this.pedidoSelecionado = pedido;
    }
  }

  listar(){
    this.pedidoService.listarAbertos().subscribe({
      next: listaPedidos => {
        this.listaPedidos = listaPedidos;
      }
    })
  }

  cadastrarPedido(modalPedido : any){
    this.pedidoParaEditar = new Pedido();
    this.modalService.open(modalPedido, { size: 'md' });
    
    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Cadastrar Pedido'
  }

  editarPedido(modal: any, pedido: Pedido, indice: number) {
    this.pedidoParaEditar = Object.assign({}, pedido); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalService.open(modal, { size: 'md' });

    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Editar Pedido'
  }

  atualizarLista(mensagem: Mensagem) {
    console.log(mensagem );
    alert(mensagem.mensagem)
    this.modalService.dismissAll();
    this.listar();
    
  }
  finalizarPedido(pedido: Pedido) {
    const dataAtual = new Date();
    pedido.finalizacao = dataAtual;
  
    this.pedidoService.save(pedido).subscribe({
      next: mensagem => {
        this.listar();
        this.retorno.emit(mensagem);
        // Atualize a lista de pedidos após a finalização bem-sucedida, se necessário.
      },
      error: erro => {
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }
}
