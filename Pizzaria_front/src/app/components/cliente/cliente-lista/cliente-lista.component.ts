import { Component ,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Cliente } from 'src/app/models/cliente';
import { Mensagem } from 'src/app/models/mensagem';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-lista',
  templateUrl: './cliente-lista.component.html',
  styleUrls: ['./cliente-lista.component.scss']
})
export class ClienteListaComponent {
  lista: Cliente[] = [];

  clienteParaEditar: Cliente = new Cliente();
  indiceSelecionadoParaEdicao!: number;
  
  modalService = inject(NgbModal);
  clienteService = inject(ClienteService);

  constructor() {
    this.listar();
  }
  

  listar(){
    this.clienteService.listar().subscribe({
      next: lista => {
        this.lista = lista;
      }
    })
  }

    cadastrar(modalCliente : any){
      this.clienteParaEditar = new Cliente();
      this.modalService.open(modalCliente, { size: 'md' });
      
      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Cadastrar Cliente'
    }

    editar(modal: any, cliente: Cliente, indice: number) {
      this.clienteParaEditar = Object.assign({}, cliente); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
      Object.assign(this.clienteParaEditar.enderecos, cliente.enderecos);
      this.indiceSelecionadoParaEdicao = indice;
  
      this.modalService.open(modal, { size: 'md' });

      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Editar Cliente'
    }

    atualizarLista(mensagem: Mensagem) {
      console.log(mensagem );
      alert(mensagem.mensagem)
      this.modalService.dismissAll();
      this.listar();
      
    }

    excluirCliente(cliente: Cliente) {
      if (confirm(`Tem certeza de que deseja excluir ${cliente.nome}?`)) {
        this.clienteService.deletar(cliente.id).subscribe({
          next: () => {
            this.listar(); // Atualize a lista após a exclusão
            alert('Cliente excluída com sucesso!');
          },
          error: (erro) => {
            alert('Ocorreu um erro ao excluir a cliente.');
            console.error(erro);
          }
        });
      }
    }
}
