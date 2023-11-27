import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Categoria } from 'src/app/models/categoria';
import { Mensagem } from 'src/app/models/mensagem';
import { Produto } from 'src/app/models/produto';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produtos-lista',
  templateUrl: './produtos-lista.component.html',
  styleUrls: ['./produtos-lista.component.scss']
})
export class ProdutosListaComponent {
  @Output() retorno = new EventEmitter<any>();
  @Input() modoVincular: boolean = false;
  listaCategoriasOrginal: Categoria[] = [];
  listaCategoriasFiltrada: Categoria[] = [];

  categoriaService = inject(CategoriaService);
  produtoService = inject(ProdutoService);
  modalService = inject(NgbModal);
  toastr = inject(ToastrService);

  categoriaParaEditar: Categoria = new Categoria();
  categoriaParaExcluir: Categoria = new Categoria();
  produtoParaEditar: Produto = new Produto();
  produtoParaExcluir: Produto = new Produto();
  
  indiceSelecionadoParaEdicao!: number;
  categoriaAberta: number | null = null;
  modalRef!: NgbModalRef;
  tituloModal!: string;
  termoPesquisa!: "";

  constructor() {
    this.listarCategorias();
  }

  toggleCategoria(categoriaId: number) {
    if (this.categoriaAberta === categoriaId) {
      this.categoriaAberta = null;
    } else {
      this.categoriaAberta = categoriaId;
    }
  }

  listarCategorias(){
    this.categoriaService.listar().subscribe({
      next: lista => {
        this.listaCategoriasOrginal = lista;
        this.listaCategoriasFiltrada = lista;
      }
    })
  }

  atualizarLista(mensagem: Mensagem) {
    this.modalService.dismissAll();
    this.listarCategorias();
    this.retorno.emit("ok");
  }

  cadastrarCategoria(modalCategoria : any){
    this.categoriaParaEditar = new Categoria();
    this.modalService.open(modalCategoria, { size: 'md' });

    this.tituloModal = "Cadastrar Categoria";
  }

  editarCategoria(modal: any, categoria: Categoria, indice: number) {
    this.categoriaParaEditar = Object.assign({}, categoria); 
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'md' });

    this.tituloModal = "Editar Categoria";
  }

  excluirCategoria(modal: any, categoria: Categoria, indice: number) {
    this.categoriaParaExcluir = Object.assign({}, categoria);
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Deleter Categoria";
  }

  confirmarExclusaoCategoria(categoria: Categoria) {
    this.categoriaService.deletar(categoria.id).subscribe({
      next: (mensagem: Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.listarCategorias();
        this.modalService.dismissAll();
      },
      error: erro => {
        this.toastr.error(erro.error.mensagem);
      }
    });
  }

  cadastrarProduto(modalProduto : any){
    this.produtoParaEditar = new Produto();
    this.modalService.open(modalProduto, { size: 'md' ,scrollable: true});

    this.tituloModal = "Cadastrar Produto";
  }

  editarProduto(modal: any, produto: Produto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto); 
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'md' });

    this.tituloModal = "Editar Produto";
  }

  excluirProduto(modal: any, produto: Produto, indice: number) {
    this.produtoParaExcluir = Object.assign({}, produto);
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Deleter Produto";
  }

  confirmarExclusaoProduto(produto: Produto) {
    this.produtoService.deletar(produto.id).subscribe({
      next: (mensagem: Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.modalService.dismissAll();
      },
      error: erro => {
        this.toastr.error(erro.error.mensagem);
      }
    });
  }

  vincular(categoria: Categoria){
    this.retorno.emit(categoria);
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaCategoriasFiltrada = this.listaCategoriasOrginal;
    } else {
      this.listaCategoriasFiltrada = this.listaCategoriasOrginal.filter((categoria: Categoria) => {
        const nome = categoria.nome.toLowerCase();
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}