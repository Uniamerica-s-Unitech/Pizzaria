import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Endereco } from 'src/app/models/endereco';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-endereco-detalhes',
  templateUrl: './endereco-detalhes.component.html',
  styleUrls: ['./endereco-detalhes.component.scss']
})
export class EnderecoDetalhesComponent {
  @Input() endereco:Endereco = new Endereco();
  @Output() retorno = new EventEmitter<Endereco>;
  toastr = inject(ToastrService);

  salvar(formulario1: any){
    if (!formulario1.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.toastr.success('Endereço salvo com sucesso');
      this.retorno.emit(this.endereco);
    }
  }
}