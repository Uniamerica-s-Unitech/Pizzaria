import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Mensagem } from 'src/app/models/mensagem';
import { Sabor } from 'src/app/models/sabor';
import { SaborService } from 'src/app/services/sabor.service';

@Component({
  selector: 'app-sabores-detalhes',
  templateUrl: './sabores-detalhes.component.html',
  styleUrls: ['./sabores-detalhes.component.scss']
})
export class SaboresDetalhesComponent {
  @Input() sabor:Sabor = new Sabor();
  @Output() retorno = new EventEmitter<Mensagem>;

  saborService = inject(SaborService);
  toastr = inject(ToastrService);

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.saborService.save(this.sabor).subscribe({
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