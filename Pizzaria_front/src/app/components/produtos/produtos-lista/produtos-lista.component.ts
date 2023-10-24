import { Component ,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Categoria } from 'src/app/models/categoria';
import { Mensagem } from 'src/app/models/mensagem';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-produtos-lista',
  templateUrl: './produtos-lista.component.html',
  styleUrls: ['./produtos-lista.component.scss']
})
export class ProdutosListaComponent {
  
}
