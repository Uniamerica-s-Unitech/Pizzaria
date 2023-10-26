import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Cliente } from 'src/app/models/cliente';
import { Endereco } from 'src/app/models/endereco';
import { Mensagem } from 'src/app/models/mensagem';
import { Pedido } from 'src/app/models/pedido';
import { Produto } from 'src/app/models/produto';
import { ClienteService } from 'src/app/services/cliente.service';
import { PedidoService } from 'src/app/services/pedido.service';
import { SaborService } from 'src/app/services/sabor.service';

@Component({
  selector: 'app-pedido-detalhes',
  templateUrl: './pedido-detalhes.component.html',
  styleUrls: ['./pedido-detalhes.component.scss']
})
export class PedidoDetalhesComponent {
  @Input() pedido:Pedido = new Pedido();
  @Output() retorno = new EventEmitter<Mensagem>;

  pedidoService = inject(PedidoService);
  saborService = inject(SaborService);
  clienteService = inject(ClienteService);

  listaClientes: Cliente[] = [];

  modalService = inject(NgbModal);
  produtoParaEditar: Produto = new Produto();
  modalRef!: NgbModalRef;
  indiceSelecionadoParaEdicao!: number;
  tituloModal!: string;
  enderecoSelecionado: Endereco | null = null;


  constructor() {
    this.carregarClientes();
  }

  carregarClientes(){
    this.clienteService.listar().subscribe({
      next: lista => { // QUANDO DÁ CERTO
        this.listaClientes = lista;
      }
    });
  }

  carregarEnderecos() {
    if (this.pedido.clienteId && this.pedido.clienteId.enderecos) {
      // Defina os endereços do cliente selecionado no pedido
      this.pedido.clienteId.enderecos = this.pedido.clienteId.enderecos;
      console.log('Endereços carregados:', this.pedido.clienteId.enderecos);
    } else {
      // Limpe os endereços do cliente no pedido se nenhum cliente estiver selecionado
      this.pedido.clienteId.enderecos = [];
    }
  }


  adicionarProduto(modalListaProdutos : any){
    this.produtoParaEditar = new Produto();
    this.produtoParaEditar.id = -1;
    this.modalRef = this.modalService.open(modalListaProdutos, { size: 'sm' });
    
    this.modalRef.result.then((produtoSelecionado: Produto) => {
      // Aqui você recebe o produto selecionado do modal e pode adicioná-lo ao seu pedido
      if (produtoSelecionado) {
        this.pedido.produtos.push(produtoSelecionado);
      }
    });

    this.tituloModal = "Adicionar Produto";
  }

  atualizarLista(produto: Produto) {

    if(this.pedido.produtos == null)
    this.pedido.produtos = [];

    if(produto.id >= 0){
      let index = this.pedido.produtos.findIndex(item => produto.id === item.id);
      this.pedido.produtos[index] = Object.assign({}, produto);
    }else{
      produto.id = 0;
      this.pedido.produtos.push(Object.assign({}, produto));
    }

    this.modalRef.dismiss();
  }

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ

    this.pedidoService.save(this.pedido).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  editarProduto(modal: any, produto: Produto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalRef = this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Editar Produto";
  }

  excluirProduto(index : number) {
    this.pedido.produtos.splice(index , 1);
  }
}
