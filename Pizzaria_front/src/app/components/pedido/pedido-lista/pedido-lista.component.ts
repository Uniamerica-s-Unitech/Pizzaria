import { Component ,Input,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from 'src/app/models/pedido';
import { Mensagem } from 'src/app/models/mensagem';
import { PedidoService } from 'src/app/services/pedido.service';
import { Produto } from 'src/app/models/produto';

@Component({
  selector: 'app-pedido-lista',
  templateUrl: './pedido-lista.component.html',
  styleUrls: ['./pedido-lista.component.scss']
})
export class PedidoListaComponent {
  listaPedidos: Pedido[] = [];
  @Input() produto:Produto = new Produto();

  pedidoParaEditar: Pedido = new Pedido();
  indiceSelecionadoParaEdicao!: number;
  
  modalService = inject(NgbModal);
  pedidoService = inject(PedidoService);

  pedidoAberto: number | null = null;

  constructor() {
    this.listar();
  }

  togglePedido(pedidoId: number) {
    if (this.pedidoAberto === pedidoId) {
      this.pedidoAberto = null;
    } else {
      this.pedidoAberto = pedidoId;
    }
  }
  

  listar(){
    this.pedidoService.listarAbertos().subscribe({
      next: listaPedidos => {
        this.listaPedidos = listaPedidos;
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
