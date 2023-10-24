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
  listaCategoria: Categoria[] = [];
  @Input() produto:Produto = new Produto();
  @Output() retorno = new EventEmitter<Mensagem>;

  produtoService = inject(ProdutoService);
  categoriaService = inject(CategoriaService);

  constructor() {
    this.listarCategorias();
  }

  listarCategorias() {
    this.categoriaService.listar().subscribe((categorias: Categoria[]) => {
      this.listaCategoria = categorias;
    });
  }

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ

    this.produtoService.save(this.produto).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }
}
