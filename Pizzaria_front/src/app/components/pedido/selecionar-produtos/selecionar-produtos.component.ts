import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Mensagem } from 'src/app/models/mensagem';
import { Produto } from 'src/app/models/produto';
import { Sabor } from 'src/app/models/sabor';
import { ProdutoService } from 'src/app/services/produto.service';
import { SaborService } from 'src/app/services/sabor.service';

@Component({
  selector: 'app-selecionar-produtos',
  templateUrl: './selecionar-produtos.component.html',
  styleUrls: ['./selecionar-produtos.component.scss']
})
export class SelecionarProdutosComponent {
  @Input() produto: Produto = new Produto();
  @Output() retorno = new EventEmitter<Produto>;

  produtoService = inject(ProdutoService);

  listaProdutos: Produto[] = [];

  constructor() {
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
    this.produto.id = this.produto.id;
    this.produto.nome = this.produto.nome;
    this.produto.valor = this.produto.valor;
    this.produto.sabores = this.produto.sabores;

    this.retorno.emit(this.produto);
  }
  
}
