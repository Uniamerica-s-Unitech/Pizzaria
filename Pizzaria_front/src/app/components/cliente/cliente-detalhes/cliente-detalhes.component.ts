import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Cliente } from 'src/app/models/cliente';
import { Endereco } from 'src/app/models/endereco';
import { Mensagem } from 'src/app/models/mensagem';
import { ClienteService } from 'src/app/services/cliente.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cliente-detalhes',
  templateUrl: './cliente-detalhes.component.html',
  styleUrls: ['./cliente-detalhes.component.scss']
})  
export class ClienteDetalhesComponent {
  @Input() cliente: Cliente = new Cliente();
  @Output() retorno = new EventEmitter<Mensagem>;

  clienteService = inject(ClienteService);
  modalService = inject(NgbModal);
  toastr = inject(ToastrService);
  
  enderecoParaEditar: Endereco = new Endereco();
  modalRef!: NgbModalRef;
  indiceSelecionadoParaEdicao!: number;
  tituloModal!: string;

  atualizarLista(endereco: Endereco) {
    if(this.cliente.enderecos == null)
    this.cliente.enderecos = [];

    if(endereco.id >= 0){
      let index = this.cliente.enderecos.findIndex(item => endereco.id === item.id);
      this.cliente.enderecos[index] = Object.assign({}, endereco);
    }else{
      endereco.id = 0;
      this.cliente.enderecos.push(Object.assign({}, endereco));
    }
    this.modalRef.dismiss();
  }

  cadastrarEndereco(modalEndereco : any){
    this.enderecoParaEditar = new Endereco();
    this.enderecoParaEditar.id = -1;
    this.modalRef = this.modalService.open(modalEndereco, { size: 'sm' });
    
    this.tituloModal = "Cadastrar Endereço";
  }

  editarEndereco(modal: any, endereco: Endereco, indice: number) {
    this.enderecoParaEditar = Object.assign({}, endereco); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
    this.indiceSelecionadoParaEdicao = indice;

    this.modalRef = this.modalService.open(modal, { size: 'sm' });

    this.tituloModal = "Editar Cliente";
  }

  excluirEndereco(index : number) {
    if (confirm(`Tem certeza de que deseja excluir este endereço?`)) {
        this.cliente.enderecos.splice(index , 1);
    }
  }

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.clienteService.save(this.cliente).subscribe({
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
