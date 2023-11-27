import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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
  toastr = inject(ToastrService);

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.categoriaService.save(this.categoria).subscribe({
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
}