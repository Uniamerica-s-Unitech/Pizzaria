import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Categoria } from 'src/app/models/categoria';
import { Mensagem } from 'src/app/models/mensagem';
import { Produto } from 'src/app/models/produto';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produtos-detalhes',
  templateUrl: './produtos-detalhes.component.html',
  styleUrls: ['./produtos-detalhes.component.scss']
})
export class ProdutosDetalhesComponent {
  listaProdutos: Produto[] = [];
  listaCategorias: Categoria[] = [];
  @Input() produto:Produto = new Produto();
  @Input() categoria:Categoria = new Categoria();
  @Output() retorno = new EventEmitter<Mensagem>;

  produtoService = inject(ProdutoService);
  categoriaService = inject(CategoriaService);

  constructor() {

    this.carregarCategorias();

  }

  carregarCategorias(){
    this.categoriaService.listar().subscribe({
      next: lista => { // QUANDO DÁ CERTO
        this.listaCategorias = lista;
      },
      error: (mensagem:Mensagem) => { // QUANDO DÁ ERRO
        alert(mensagem);
        console.error(mensagem);
      }
    });
  }

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ
    console.log("Valor de categoriaId:", this.produto.categoriaId);
    
    this.produtoService.save(this.produto).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: (mensagem:Mensagem) => { // QUANDO DÁ ERRO
        alert(mensagem);
        console.error(mensagem);
      }
    });
  }
}
