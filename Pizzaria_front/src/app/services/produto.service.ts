import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  API: string = 'http://3.22.97.175:8080/produto';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}` + "/lista");
  }

  save(produto: Produto): Observable<Mensagem> {
    if (produto.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${produto.id}`, produto);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, produto);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
