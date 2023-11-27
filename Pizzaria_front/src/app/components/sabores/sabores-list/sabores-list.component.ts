import { Component ,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Mensagem } from 'src/app/models/mensagem';
import { Sabor } from 'src/app/models/sabor';
import { SaborService } from 'src/app/services/sabor.service';

@Component({
  selector: 'app-sabores-list',
  templateUrl: './sabores-list.component.html',
  styleUrls: ['./sabores-list.component.scss']
})
export class SaboresListComponent {
  @Output() retorno = new EventEmitter<any>();
  @Input() modoVincular: boolean = false;

  listaSaboresOrginal: Sabor[] = [];
  listaSaboresFiltrada: Sabor[] = [];

  modalService = inject(NgbModal);
  saborService = inject(SaborService);
  toastr = inject(ToastrService);

  saborParaEditar: Sabor = new Sabor();
  saborParaExcluir: Sabor = new Sabor();

  indiceSelecionadoParaEdicao!: number;
  modalRef!: NgbModalRef;
  tituloModal!: string;
  termoPesquisa!: "";
  
  constructor() {
    this.listarSabores();
  }
  
  listarSabores(){
    this.saborService.listar().subscribe({
      next: lista => {
        this.listaSaboresOrginal = lista;
        this.listaSaboresFiltrada = lista;
      }
    })
  }

  atualizarLista(mensagem: Mensagem) {
    this.modalService.dismissAll();
    this.listarSabores();
    this.retorno.emit("ok");
  }

  cadastrarSabor(modalSabor : any){
    this.saborParaEditar = new Sabor();
    this.modalService.open(modalSabor, { size: 'md' });
    
    this.tituloModal = "Cadastrar Sabor";
  }

  editarSabor(modal: any, sabor: Sabor, indice: number) {
    this.saborParaEditar = Object.assign({}, sabor); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalService.open(modal, { size: 'md' });

    this.tituloModal = "Editar Sabor";
  }

  excluirSabor(modal: any, sabor: Sabor, indice: number) {
    this.saborParaExcluir = Object.assign({}, sabor);
    this.indiceSelecionadoParaEdicao = indice;

    this.modalService.open(modal, { size: 'sm' });
    this.tituloModal = "Deleter Sabor";

  }

  confirmarExclusao(sabor: Sabor) {
    this.saborService.deletar(sabor.id).subscribe({
      next: (mensagem: Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.listarSabores();
        this.modalService.dismissAll();
      },
      error: erro => {
        this.toastr.error(erro.error.mensagem);
      }
    });
  }

  vincular(sabor: Sabor){
    this.retorno.emit(sabor);
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaSaboresFiltrada = this.listaSaboresOrginal;
    } else {
      this.listaSaboresFiltrada = this.listaSaboresOrginal.filter((sabor: Sabor) => {
        const nome = sabor.nome.toLowerCase();
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}