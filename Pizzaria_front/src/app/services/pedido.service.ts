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


  listar(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.API}` + "/lista");
  }

  save(cliente: Pedido): Observable<Mensagem> {
    if (cliente.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${cliente.id}`, cliente);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, cliente);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
