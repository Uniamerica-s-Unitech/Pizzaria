import { Component, Input, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
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
  listaCategoria: Categoria[] = [];
  categoriaParaEditar: Categoria = new Categoria();
  produtoParaEditar: Produto = new Produto();
  modalService = inject(NgbModal);
  categoriaService = inject(CategoriaService);
  produtoService = inject(ProdutoService);
  @Input() categoria:Categoria = new Categoria();

  indiceSelecionadoParaEdicao!: number;
  categoriaAberta: number | null = null;

  constructor() {
    this.listar();
  }

  toggleCategoria(categoriaId: number) {
    if (this.categoriaAberta === categoriaId) {
      this.categoriaAberta = null;
    } else {
      this.categoriaAberta = categoriaId;
    }
  }


  listar(){
    this.categoriaService.listar().subscribe({
      next: listaCategoria => {
        this.listaCategoria = listaCategoria;
      }
    })
  }

  cadastrarCategoria(modalCategoria : any){
    this.categoriaParaEditar = new Categoria();
    this.modalService.open(modalCategoria, { size: 'md' });

    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Cadastrar Categoria'
  }

  editarCategoria(modal: any, categoria: Categoria, indice: number) {
    this.categoriaParaEditar = Object.assign({}, categoria); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalService.open(modal, { size: 'md' });

    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Editar Categoria'
  }

  atualizarLista(mensagem: Mensagem) {
    console.log(mensagem );
    alert(mensagem.mensagem)
    this.modalService.dismissAll();
    this.listar();

  }

  excluirCategoria(categoria: Categoria) {
    if (confirm(`Tem certeza de que deseja excluir esta categoria?`)) {
      this.categoriaService.deletar(categoria.id).subscribe({
        next: (mensagem:Mensagem) => {
          this.listar(); // Atualize a lista após a exclusão
          alert(mensagem.mensagem);
        },
        error: (mensagem:Mensagem) => {
          alert(mensagem.mensagem);
        }
      });
    }
  }

  cadastrarProduto(modalProduto : any){
    this.produtoParaEditar = new Produto();
    this.modalService.open(modalProduto, { size: 'md' });

    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Cadastrar Categoria'
  }

  editarProduto(modal: any, produto: Produto, indice: number) {
    this.produtoParaEditar = Object.assign({}, produto); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalService.open(modal, { size: 'md' });

    const element: HTMLElement = document.getElementById('h4') as HTMLElement 
    element.innerHTML = 'Editar Categoria'
  }

  excluirProduto(produto: Produto,index:number) {
    if (confirm(`Tem certeza de que deseja excluir esta produto?`)) {
      this.produtoService.deletar(produto.id).subscribe({
        next: (mensagem:Mensagem) => {
          alert(mensagem.mensagem);
          this.editarCategoriaExcluirProduto(produto);
          this.listar(); // Atualize a lista após a exclusão

        },
        error: (mensagem:Mensagem) => {
          alert(mensagem.mensagem);
        }
      });
    }
  }

  editarCategoriaExcluirProduto(produto: Produto) {
    // Encontre a categoria relacionada ao produto que você está excluindo
    const categoria = this.listaCategoria.find(item => item.produtos.some(produtoId => produtoId.id === produto.id));

    if (categoria) {
      // Edite a categoria para remover o produto da lista de produtos
      const produtoIndex = categoria.produtos.findIndex(produtoId => produtoId.id === produto.id);
      if (produtoIndex !== -1) {
        categoria.produtos.splice(produtoIndex, 1);
      }

      // Agora, você pode chamar o serviço para editar a categoria no backend
      this.categoriaService.save(categoria).subscribe({
        next: () => {
          // Atualize a lista após a edição da categoria
          this.listar();
        }
      });
    }
  }
}
