import { Component ,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from 'src/app/models/pedido';
import { Cliente } from 'src/app/models/cliente';
import { Produto } from 'src/app/models/produto';
import { Mensagem } from 'src/app/models/mensagem';
import { PedidoService } from 'src/app/services/pedido.service';
@Component({
  selector: 'app-historico-pedidos',
  templateUrl: './historico-pedidos.component.html',
  styleUrls: ['./historico-pedidos.component.scss']
})
export class HistoricoPedidosComponent {
  listaPedidos: Pedido[] = [];

  pedidoSelecionado: number | null = null;
  
  modalService = inject(NgbModal);
  pedidoService = inject(PedidoService);

  modalRef!: NgbModalRef;


  constructor() {
    this.listarHistoricos();
  }

  mostrarDetalhes(pedido: number) {
    if (this.pedidoSelecionado === pedido) {
      this.pedidoSelecionado = null;
    } else {
      this.pedidoSelecionado = pedido;
    }
  }

  listarHistoricos(){
    this.pedidoService.listarFinalizados().subscribe({
      next: listaPedidos => {
        this.listaPedidos = listaPedidos;
      }
    })
  }

  
}
