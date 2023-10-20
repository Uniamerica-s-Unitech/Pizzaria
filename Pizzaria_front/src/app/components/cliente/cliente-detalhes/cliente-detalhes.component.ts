import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-detalhes',
  templateUrl: './cliente-detalhes.component.html',
  styleUrls: ['./cliente-detalhes.component.scss']
})
export class ClienteDetalhesComponent {
  @Input() cliente:Cliente = new Cliente();
  @Output() retorno = new EventEmitter<Cliente>;

  clienteService = inject(ClienteService);

  constructor() {}

  salvar() {
    //ISSO AQUI SERVE PARA EDITAR OU ADICIONAR... TANTO FAZ

    this.clienteService.save(this.cliente).subscribe({
      next: cliente => { // QUANDO DÁ CERTO
        this.retorno.emit(cliente);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }
}
