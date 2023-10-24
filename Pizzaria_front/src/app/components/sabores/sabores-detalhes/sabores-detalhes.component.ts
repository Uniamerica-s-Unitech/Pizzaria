import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
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

  constructor() {}

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ

    this.saborService.save(this.sabor).subscribe({
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
