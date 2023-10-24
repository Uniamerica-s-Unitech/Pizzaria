import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';
import { Categoria } from '../models/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  API: string = 'http://localhost:8080/categoria';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.API}` + "/lista");
  }

  save(categoria: Categoria): Observable<Mensagem> {
    if (categoria.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${categoria.id}`, categoria);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, categoria);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
