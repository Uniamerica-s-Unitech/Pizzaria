import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment-timezone';
import 'moment-timezone';
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
  @Input() pedido: Pedido = new Pedido();
  @Output() retorno = new EventEmitter<Mensagem>();

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
    this.produtoParaEditar = new Produto();
    this.produtoParaEditar.id = -1;
    this.modalRef = this.modalService.open(modalListaProdutos, { size: 'sm' });

    this.tituloModal = "Adicionar Produto";
  }

  atualizarLista(produto: Produto) {

    if (this.pedido.produtos == null) this.pedido.produtos = [];

    this.pedido.produtos.push(Object.assign({}, produto));

    /*if (produto.id >= 0) {
      let index = this.pedido.produtos.findIndex(item => produto.id === item.id);
      this.pedido.produtos[index] = Object.assign({}, produto);
    } else if (produto.id < 0) {
      produto.id = 0; 
      this.pedido.produtos.push(Object.assign({}, produto));
    }  */
    console.log(this.pedido);

    this.modalRef.dismiss();
  }

  salvar() {
    let dataAtual = moment.tz('America/Brasilia ');
    this.pedido.solicitacao = dataAtual.toDate();

    this.pedido.valorTotal = 0;

  // Use um loop para somar os valores dos produtos
    for (const produto of this.pedido.produtos) {
      this.pedido.valorTotal += produto.valor;
    }

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

  editarProduto(modal: any, produto: Produto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto);
    this.indiceSelecionadoParaEdicao = indice;

    this.modalRef = this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Editar Produto";
  }

  excluirProduto(index: number) {
    this.pedido.produtos.splice(index, 1);
  }
}
