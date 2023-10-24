import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';
import { Sabor } from '../models/sabor';

@Injectable({
  providedIn: 'root'
})
export class SaborService {
  API: string = 'http://localhost:8080/sabor';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Sabor[]> {
    return this.http.get<Sabor[]>(`${this.API}` + "/lista");
  }

  save(cliente: Sabor): Observable<Mensagem> {
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
