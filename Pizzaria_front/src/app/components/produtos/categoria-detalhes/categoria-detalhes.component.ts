import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Categoria } from 'src/app/models/categoria';
import { Mensagem } from 'src/app/models/mensagem';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-detalhes',
  templateUrl: './categoria-detalhes.component.html',
  styleUrls: ['./categoria-detalhes.component.scss']
})
export class CategoriaDetalhesComponent {
  @Input() categoria:Categoria = new Categoria();
  @Output() retorno = new EventEmitter<Mensagem>;

  categoriaService = inject(CategoriaService);

  constructor() {}

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ

    this.categoriaService.save(this.categoria).subscribe({
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
