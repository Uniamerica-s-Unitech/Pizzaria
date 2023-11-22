import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';
import { Mensagem } from '../models/mensagem';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  API: string = 'http://localhost:8080/cliente';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.API}` + "/lista");
  }

  save(cliente: Cliente): Observable<Mensagem> {
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
