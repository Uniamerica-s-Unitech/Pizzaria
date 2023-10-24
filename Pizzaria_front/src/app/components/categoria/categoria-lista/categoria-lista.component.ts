import { Component, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Categoria } from 'src/app/models/categoria';
import { Mensagem } from 'src/app/models/mensagem';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-lista',
  templateUrl: './categoria-lista.component.html',
  styleUrls: ['./categoria-lista.component.scss']
})
export class CategoriaListaComponent {
  listaCategoria: Categoria[] = [];

  categoriaParaEditar: Categoria = new Categoria();
  indiceSelecionadoParaEdicao!: number;
  
  modalService = inject(NgbModal);
  categoriaService = inject(CategoriaService);

  showAccordion = false;

  constructor() {
    this.listar();
  }

  toggleAccordion() {
    this.showAccordion = !this.showAccordion; // Alternar entre mostrar/ocultar o accordion
  }
  

  listar(){
    this.categoriaService.listar().subscribe({
      next: listaCategoria => {
        this.listaCategoria = listaCategoria;
      }
    })
  }

    cadastrarCategoria(modalCategoria : any){
      this.categoriaParaEditar = new Categoria();
      this.modalService.open(modalCategoria, { size: 'md' });
      
      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Cadastrar Categoria'
    }

    editarCategoria(modal: any, categoria: Categoria, indice: number) {
      this.categoriaParaEditar = Object.assign({}, categoria); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
      this.indiceSelecionadoParaEdicao = indice;
  
      this.modalService.open(modal, { size: 'md' });

      const element: HTMLElement = document.getElementById('h4') as HTMLElement 
      element.innerHTML = 'Editar Categoria'
    }

    atualizarLista(mensagem: Mensagem) {
      console.log(mensagem );
      alert(mensagem.mensagem)
      this.modalService.dismissAll();
      this.listar();
      
    }

    excluirCategoria(categoria: Categoria) {
      if (confirm(`Tem certeza de que deseja excluir ${categoria.nome}?`)) {
        this.categoriaService.deletar(categoria.id).subscribe({
          next: () => {
            this.listar(); // Atualize a lista após a exclusão
            alert('Categoria excluída com sucesso!');
          },
          error: (erro) => {
            alert('Ocorreu um erro ao excluir a categoria.');
            console.error(erro);
          }
        });
      }
    }
}
