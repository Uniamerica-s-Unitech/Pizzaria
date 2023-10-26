import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Produto } from 'src/app/models/produto';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-selecionar-produtos',
  templateUrl: './selecionar-produtos.component.html',
  styleUrls: ['./selecionar-produtos.component.scss']
})
export class SelecionarProdutosComponent {
  @Input() produto: Produto = new Produto();
  @Output() retorno = new EventEmitter<Produto>();

  produtoService: ProdutoService;
  listaProdutos: Produto[] = [];

  constructor(produtoService: ProdutoService) {
    this.produtoService = produtoService;
    this.carregarProdutos();
  }

  carregarProdutos() {
    this.produtoService.listar().subscribe({
      next: (lista) => {
        this.listaProdutos = lista;
      }
    });
  }

  salvar() {
    // Não emita o produto diretamente, apenas mantenha-o no componente pai
    console.log(this.produto);
    this.retorno.emit(this.produto);
  }
}