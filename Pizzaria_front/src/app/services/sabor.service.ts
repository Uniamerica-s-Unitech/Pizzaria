import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';
import { Sabor } from '../models/sabor';

@Injectable({
  providedIn: 'root'
})
export class SaborService {
  API: string = 'http://3.22.97.175:8080/sabor';
  http = inject(HttpClient);

  constructor() { }


  listar(): Observable<Sabor[]> {
    return this.http.get<Sabor[]>(`${this.API}` + "/lista");
  }

  save(sabor: Sabor): Observable<Mensagem> {
    if (sabor.id) {
      return this.http.put<Mensagem>(this.API+"/"+`${sabor.id}`, sabor);
    } else {
      return this.http.post<Mensagem>(this.API, sabor);
    }
  }

  deletar(id: number): Observable<Mensagem> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}
