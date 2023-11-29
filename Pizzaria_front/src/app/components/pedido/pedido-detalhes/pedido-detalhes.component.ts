import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Mensagem } from 'src/app/models/mensagem';
import { Pedido } from 'src/app/models/pedido';
import { PedidoProduto } from 'src/app/models/pedido-produto';
import { PedidoService } from 'src/app/services/pedido.service';
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
  modalService = inject(NgbModal);
  toastr = inject(ToastrService);
  modalRef!: NgbModalRef;

  produtoParaEditar: PedidoProduto = new PedidoProduto();
  indiceSelecionadoParaEdicao!: number;
  tituloModal!: string;

  atualizarLista(produto: PedidoProduto) {
    if (this.pedido.pedidoProdutoList == null)
      this.pedido.pedidoProdutoList = [];
    if (this.indiceSelecionadoParaEdicao == -1)
      this.pedido.pedidoProdutoList.push(Object.assign({}, produto));
    else {
      this.pedido.pedidoProdutoList[this.indiceSelecionadoParaEdicao] = Object.assign({}, produto);
    }
    this.modalRef.dismiss();
  }

  adicionarProduto(modalListaProdutos: any) {
    this.indiceSelecionadoParaEdicao = -1;
    this.produtoParaEditar = new PedidoProduto();
    this.modalRef = this.modalService.open(modalListaProdutos, { size: 'lg' });

    this.tituloModal = "Adicionar Produto";
  }

  editarProduto(modal: any, produto: PedidoProduto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto);
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'lg' });

    this.tituloModal = "Editar Produto";
  }

  excluirProduto(index: number) {
    this.pedido.pedidoProdutoList.splice(index, 1);
  }

  salvar(formulario: any) {
    this.pedido.valorTotal = 0;

    // Use um loop para somar os valores dos produtos
    if (this.pedido.pedidoProdutoList != null)
      for (const produto of this.pedido.pedidoProdutoList) {
        this.pedido.valorTotal += produto.produtoId.valor;
      }

    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.pedidoService.save(this.pedido).subscribe({
        next: mensagem => {
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => {
          this.toastr.error(erro.error.mensagem);
        }
      });
    }
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