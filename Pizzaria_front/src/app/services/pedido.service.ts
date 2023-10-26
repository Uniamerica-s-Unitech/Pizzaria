import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';
import { Pedido } from '../models/pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  API: string = 'http://localhost:8080/pedido';
  http = inject(HttpClient);

  constructor() { }


  listarAbertos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.API}` + "/abertos");
  }
  listarFinalizados(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.API}` + "/historico");
  }

  save(pedido: Pedido): Observable<Mensagem> {
    if (pedido.id) {
      return this.http.put<Mensagem>(this.API+"/"+`${pedido.id}`, pedido);
    } else {
      return this.http.post<Mensagem>(this.API, pedido);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
