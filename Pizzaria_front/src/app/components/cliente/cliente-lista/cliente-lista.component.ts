import { Component ,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Cliente } from 'src/app/models/cliente';
import { Mensagem } from 'src/app/models/mensagem';
import { ClienteService } from 'src/app/services/cliente.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cliente-lista',
  templateUrl: './cliente-lista.component.html',
  styleUrls: ['./cliente-lista.component.scss']
})
export class ClienteListaComponent {
  @Output() retorno = new EventEmitter<any>();
  @Input() modoVincular: boolean = false;

  listaClientesOrginal: Cliente[] = [];
  listaClientesFiltrada: Cliente[] = [];

  modalService = inject(NgbModal);
  clienteService = inject(ClienteService);
  toastr = inject(ToastrService);

  clienteParaEditar: Cliente = new Cliente();
  clienteParaExcluir: Cliente = new Cliente();

  indiceSelecionadoParaEdicao!: number;
  modalRef!: NgbModalRef;
  tituloModal!: string;
  termoPesquisa!: "";

  ngOnInit(){
    this.listarClientes();
  }
  constructor() {
    this.listarClientes();
  }
  
  listarClientes(){
    this.clienteService.listar().subscribe({
      next: lista => {
        this.listaClientesOrginal = lista;
        this.listaClientesFiltrada = lista;
      }
    })
  }
  atualizarLista(mensagem: Mensagem) {
    this.modalService.dismissAll();
    this.listarClientes();
    this.retorno.emit("ok");
  }

  cadastrarCliente(modalCliente : any){
    this.clienteParaEditar = new Cliente();
    this.modalService.open(modalCliente, { size: 'lg' ,scrollable: true});
    
    this.tituloModal = "Cadastrar Cliente";
  }

  editarCliente(modal: any, cliente: Cliente, indice: number) {
    this.clienteParaEditar = Object.assign({}, cliente);
    Object.assign(this.clienteParaEditar.enderecos, cliente.enderecos);
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'lg' ,scrollable: true});

    this.tituloModal = "Editar Cliente";
  }

  excluirCliente(modal: any, cliente: Cliente, indice: number) {
    this.clienteParaExcluir = Object.assign({}, cliente);
    this.indiceSelecionadoParaEdicao = indice;
    this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Deleter Cliente";
  }

  confirmarExclusao(cliente: Cliente) {
    this.clienteService.deletar(cliente.id).subscribe({
      next: (mensagem: Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.listarClientes();
        this.modalService.dismissAll();
      },
      error: erro => {
        this.toastr.error(erro.error.mensagem);
      }
    });
  }

  vincular(cliente: Cliente){
    this.retorno.emit(cliente);
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaClientesFiltrada = this.listaClientesOrginal;
    } else {
      this.listaClientesFiltrada = this.listaClientesOrginal.filter((cliente: Cliente) => {
        const nome = cliente.nome.toLowerCase();
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}