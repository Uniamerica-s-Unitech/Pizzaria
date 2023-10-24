import { Component ,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Mensagem } from 'src/app/models/mensagem';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-pedido-lista',
  templateUrl: './pedido-lista.component.html',
  styleUrls: ['./pedido-lista.component.scss']
})
export class PedidoListaComponent {
  listaPedido: Pedido[] = [];

  pedidoParaEditar: Pedido = new Pedido();
  indiceSelecionadoParaEdicao!: number;
  
  modalService = inject(NgbModal);
  pedidoService = inject(PedidoService);

  constructor() {
    this.listar();
  }
  

  listar(){
    this.pedidoService.listar().subscribe({
      next: listaPedido => {
        this.listaPedido = listaPedido;
      }
    })
  }

    cadastrarPedido(modalPedido : any){
      this.pedidoParaEditar = new Pedido();
      this.modalService.open(modalPedido, { size: 'md' });
      
      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Cadastrar Pedido'
    }

    editarPedido(modal: any, pedido: Pedido, indice: number) {
      this.pedidoParaEditar = Object.assign({}, pedido); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
      this.indiceSelecionadoParaEdicao = indice;
  
      this.modalService.open(modal, { size: 'md' });

      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Editar Pedido'
    }

    atualizarLista(mensagem: Mensagem) {
      console.log(mensagem );
      alert(mensagem.mensagem)
      this.modalService.dismissAll();
      this.listar();
      
    }
}
