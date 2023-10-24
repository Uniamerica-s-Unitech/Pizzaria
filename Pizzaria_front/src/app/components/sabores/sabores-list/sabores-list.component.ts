import { Component ,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Mensagem } from 'src/app/models/mensagem';
import { Sabor } from 'src/app/models/sabor';
import { SaborService } from 'src/app/services/sabor.service';

@Component({
  selector: 'app-sabores-list',
  templateUrl: './sabores-list.component.html',
  styleUrls: ['./sabores-list.component.scss']
})
export class SaboresListComponent {
  lista: Sabor[] = [];

  saborParaEditar: Sabor = new Sabor();
  indiceSelecionadoParaEdicao!: number;
  
  modalService = inject(NgbModal);
  saborService = inject(SaborService);

  constructor() {
    this.listar();
  }
  

  listar(){
    this.saborService.listar().subscribe({
      next: lista => {
        this.lista = lista;
      }
    })
  }

    cadastrarSabor(modalSabor : any){
      this.saborParaEditar = new Sabor();
      this.modalService.open(modalSabor, { size: 'md' });
      
      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Cadastrar Sabor'
    }

    editarSabor(modal: any, sabor: Sabor, indice: number) {
      this.saborParaEditar = Object.assign({}, sabor); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
      this.indiceSelecionadoParaEdicao = indice;
  
      this.modalService.open(modal, { size: 'md' });

      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Editar Sabor'
    }

    atualizarLista(mensagem: Mensagem) {
      console.log(mensagem );
      alert(mensagem.mensagem)
      this.modalService.dismissAll();
      this.listar();
      
    }

    excluirSabor(sabor: Sabor) {
      if (confirm(`Tem certeza de que deseja excluir este sabor?`)) {
        this.saborService.deletar(sabor.id).subscribe({
          next: () => {
            this.listar(); // Atualize a lista após a exclusão
          },
          error: (erro) => {
            alert('Ocorreu um erro ao excluir a sabor.');
            console.error(erro);
          }
        });
      }
    }
}
