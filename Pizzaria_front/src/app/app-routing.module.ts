import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/layout/index/index.component';
import { ClienteListaComponent } from './components/cliente/cliente-lista/cliente-lista.component';
import { PedidoListaComponent } from './components/pedido/pedido-lista/pedido-lista.component';
import { ProdutosListaComponent } from './components/produtos/produtos-lista/produtos-lista.component';
import { SaboresListComponent } from './components/sabores/sabores-list/sabores-list.component';
import { CategoriaListaComponent } from './components/categoria/categoria-lista/categoria-lista.component';

const routes: Routes = [
  {path:"",component:IndexComponent,children:[
    {path:"cliente",component:ClienteListaComponent},
    {path:"pedidos",component:PedidoListaComponent},
    {path:"produtos",component:ProdutosListaComponent, children:[
      {path:"categoria",component:CategoriaListaComponent}
    ]},
    {path:"sabores",component:SaboresListComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
