import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  API: string = 'http://localhost:8080/produto';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}` + "/lista");
  }

  save(cliente: Produto): Observable<Mensagem> {
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
