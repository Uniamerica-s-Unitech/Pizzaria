import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment-timezone';
import 'moment-timezone';
import { Cliente } from 'src/app/models/cliente';
import { Mensagem } from 'src/app/models/mensagem';
import { Pedido } from 'src/app/models/pedido';
import { PedidoProduto } from 'src/app/models/pedido-produto';
import { ClienteService } from 'src/app/services/cliente.service';
import { PedidoService } from 'src/app/services/pedido.service';
import { SaborService } from 'src/app/services/sabor.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-pedido-detalhes',
  templateUrl: './pedido-detalhes.component.html',
  styleUrls: ['./pedido-detalhes.component.scss']
})
export class PedidoDetalhesComponent {
  @Input() modo: number = 1;
  @Input() pedido: Pedido = new Pedido();
  @Output() retorno = new EventEmitter<Mensagem>();

  pedidoService = inject(PedidoService);
  saborService = inject(SaborService);
  clienteService = inject(ClienteService);
  modalService = inject(NgbModal);
  toastr = inject(ToastrService);
  modalRef!: NgbModalRef;

  listaClientes: Cliente[] = [];


  produtoParaEditar: PedidoProduto = new PedidoProduto();

  indiceSelecionadoParaEdicao!: number;
  tituloModal!: string;

  constructor() {
    this.carregarClientes();
  }

  carregarClientes() {
    this.clienteService.listar().subscribe({
      next: lista => {
        this.listaClientes = lista;
      }
    });
  }

  carregarEnderecos() {
    if (this.pedido.clienteId && this.pedido.clienteId.enderecos) {
      this.pedido.clienteId.enderecos = this.pedido.clienteId.enderecos;
    } else {
      this.pedido.clienteId.enderecos = [];
    }
  }

  adicionarProduto(modalListaProdutos: any) {
    this.indiceSelecionadoParaEdicao = -1;
    this.produtoParaEditar = new PedidoProduto();
    this.modalRef = this.modalService.open(modalListaProdutos, { size: 'sm' });

    this.tituloModal = "Adicionar Produto";
  }

  atualizarLista(produto: PedidoProduto) {

    if (this.pedido.produtos == null)
      this.pedido.produtos = [];

    if (this.indiceSelecionadoParaEdicao == -1)
      this.pedido.produtos.push(Object.assign({}, produto));
    else {
      this.pedido.produtos[this.indiceSelecionadoParaEdicao] = Object.assign({}, produto);
    }

    /*if (produto.id >= 0) {
      let index = this.pedido.produtos.findIndex(item => produto.id === item.id);
      this.pedido.produtos[index] = Object.assign({}, produto);
    } else if (produto.id < 0) {
      produto.id = 0; 
      this.pedido.produtos.push(Object.assign({}, produto));
    }  */

    this.modalRef.dismiss();
  }

  salvar() {
    this.pedido.valorTotal = 0;

    // Use um loop para somar os valores dos produtos
    if (this.pedido.produtos != null)
      for (const produto of this.pedido.produtos) {
        this.pedido.valorTotal += produto.produtoId.valor;
      }

    console.log(this.pedido);

    this.pedidoService.save(this.pedido).subscribe({
      next: mensagem => {
        this.retorno.emit(mensagem);
      },
      error: erro => {
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  editarProduto(modal: any, produto: PedidoProduto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto);
    this.indiceSelecionadoParaEdicao = indice;

    this.modalRef = this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Editar Produto";
  }

  excluirProduto(index: number) {
    this.pedido.produtos.splice(index, 1);
  }

  retornoCliente(cliente: any) {
    this.toastr.success('Cliente vinculado com sucesso');
    this.pedido.clienteId = cliente;
    this.modalRef.dismiss();
  }

  buscar(modal: any) {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }

  byId(item1: any, item2: any) {
    if (item1 != null && item2 != null)
      return item1.id === item2.id;
    else
      return item1 === item2;
  }
}
