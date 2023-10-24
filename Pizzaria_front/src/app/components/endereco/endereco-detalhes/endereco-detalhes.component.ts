import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Endereco } from 'src/app/models/endereco';
import { Mensagem } from 'src/app/models/mensagem';

@Component({
  selector: 'app-endereco-detalhes',
  templateUrl: './endereco-detalhes.component.html',
  styleUrls: ['./endereco-detalhes.component.scss']
})
export class EnderecoDetalhesComponent {
  @Input() endereco:Endereco = new Endereco();
  @Output() retorno = new EventEmitter<Endereco>;

  salvar(){
    this.retorno.emit(this.endereco);
  }

}
