import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { PedidoProduto } from 'src/app/models/pedido-produto';
import { Produto } from 'src/app/models/produto';
import { Sabor } from 'src/app/models/sabor';
import { ProdutoService } from 'src/app/services/produto.service';
import { SaborService } from 'src/app/services/sabor.service';
import { PedidoListaComponent } from '../pedido-lista/pedido-lista.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-selecionar-produtos',
  templateUrl: './selecionar-produtos.component.html',
  styleUrls: ['./selecionar-produtos.component.scss']
})
export class SelecionarProdutosComponent implements OnInit{
  @Output() retorno = new EventEmitter<PedidoProduto>();
  @Input() pedidoProduto: PedidoProduto = new PedidoProduto(); //OBJETO

  listaProdutos: Produto[] = []; //Listagem geral de produtos do banco
  listaSabores: Sabor[] = []; //Listagem geral de sabores

  produtoService = inject(ProdutoService);
  saborService = inject(SaborService);
  toastr = inject(ToastrService);

  numSaboresSelecionados: number = 0;

  ngOnInit(){
    this.carregarProdutos();
    this.carregarSabores();
  }

  carregarProdutos() {
    this.produtoService.listar().subscribe({
      next: lista => {
        console.log(lista);
        this.listaProdutos = lista;
      }
    });
  }

  carregarSabores() {
    this.saborService.listar().subscribe({
      next: lista => {
        this.listaSabores = lista;
        
        if (this.listaSabores != null)
          for (let i = 0; i < this.listaSabores.length; i++) {
            this.listaSabores[i].selecionado = false;
            this.listaSabores[i].bloqueado = false;
          }
        this.marcarSabores();
      }
    });
  }

  marcarSabores() {
  
    if (this.listaSabores != null)
      for (let i = 0; i < this.listaSabores.length; i++) {
        if (this.pedidoProduto.sabores != null)
          for (let j = 0; j < this.pedidoProduto.sabores.length; j++) {
            if (this.listaSabores[i].id == this.pedidoProduto.sabores[j].id){
              this.listaSabores[i].selecionado = true;
            }
          }
      }

  }

  salvar() {
    let lista: Sabor[] = [];

    if (this.listaSabores != null) {
      for (let i = 0; i < this.listaSabores.length; i++) {
        if (this.listaSabores[i].selecionado)
          lista.push(this.listaSabores[i]);
      }
    }

    this.pedidoProduto.sabores = lista;
    this.retorno.emit(this.pedidoProduto);

    this.toastr.success('Produto adicionado com sucesso');
  }


  teste: any;
  verificarSabores() {
    let qtid = 0;
    if (this.listaSabores != null)
      for (let i = 0; i < this.listaSabores.length; i++) {
        if (this.listaSabores[i].selecionado)
          qtid++;
      }


      let bloquear = false;
    if (this.pedidoProduto.produtoId.tamanho == 'GG' && qtid == 4) {
      bloquear = true;
    } else if (this.pedidoProduto.produtoId.tamanho == 'G' && qtid == 3) {
      bloquear = true;
    } else if (this.pedidoProduto.produtoId.tamanho == 'M' && qtid == 2) {
      bloquear = true;
    } else if (this.pedidoProduto.produtoId.tamanho == 'P' && qtid == 1) {
      bloquear = true;
    }
    if(bloquear){
      console.log(bloquear);
      for (let i = 0; i < this.listaSabores.length; i++) {
        if (!this.listaSabores[i].selecionado)
          this.listaSabores[i].bloqueado = true;
      }
      console.log(this.listaSabores);
      
    }
    else{
      let desbloquear = false;
      if (this.pedidoProduto.produtoId.tamanho == 'GG' && qtid < 4) {
        desbloquear = true;
      } else if (this.pedidoProduto.produtoId.tamanho == 'G' && qtid < 3) {
        desbloquear = true;
      } else if (this.pedidoProduto.produtoId.tamanho == 'M' && qtid < 2) {
        desbloquear = true;
      } else if (this.pedidoProduto.produtoId.tamanho == 'P' && qtid < 1) {
        desbloquear = true;
      }
      if(desbloquear){
        console.log(desbloquear);
        for (let i = 0; i < this.listaSabores.length; i++) {
          if (!this.listaSabores[i].selecionado)
            this.listaSabores[i].bloqueado = false;
        }
        console.log(this.listaSabores);
        
      }
    }
  }

  zerarSabores(){
    if (this.listaSabores != null)
      for (let i = 0; i < this.listaSabores.length; i++) {
        this.listaSabores[i].selecionado = false;
        this.listaSabores[i].bloqueado = false;
    }
  }
  byId(item1: any, item2: any) {
    if (item1 != null && item2 != null)
      return item1.id === item2.id;
    else
      return item1 === item2;
  }
}