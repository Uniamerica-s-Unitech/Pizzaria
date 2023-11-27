import { Component ,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Mensagem } from 'src/app/models/mensagem';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-historico-pedidos',
  templateUrl: './historico-pedidos.component.html',
  styleUrls: ['./historico-pedidos.component.scss']
})
export class HistoricoPedidosComponent {
  listaPedidosOrginal: Pedido[] = [];
  listaPedidosFiltrada: Pedido[] = [];

  pedidoService = inject(PedidoService);

  tituloModal!: string;
  termoPesquisa!: "";
  active!: any;
  pedidoSelecionado: number | null = null;

  constructor() {
    this.listarPedidos();
  }

  mostrarDetalhes(pedido: number) {
    if (this.pedidoSelecionado === pedido) {
      this.pedidoSelecionado = null;
    } else {
      this.pedidoSelecionado = pedido;
    }
  }

  listarPedidos(){
    this.pedidoService.listarFinalizados().subscribe({
      next: listaPedidos => {
        this.listaPedidosOrginal = listaPedidos;
        this.listaPedidosFiltrada = listaPedidos;
      }
    })
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaPedidosFiltrada = this.listaPedidosOrginal;
    } else {
      this.listaPedidosFiltrada = this.listaPedidosOrginal.filter((pedido: Pedido) => {
        const id = pedido.id.toString().toLowerCase();
        const nome = pedido.clienteId.nome.toLowerCase();
        return (
          id.includes(termoPesquisa) ||
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}
