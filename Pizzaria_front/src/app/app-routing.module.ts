import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/layout/index/index.component';
import { ClienteListaComponent } from './components/cliente/cliente-lista/cliente-lista.component';
import { PedidoListaComponent } from './components/pedido/pedido-lista/pedido-lista.component';
import { ProdutosListaComponent } from './components/produtos/produtos-lista/produtos-lista.component';
import { SaboresListComponent } from './components/sabores/sabores-list/sabores-list.component';
import { LoginComponent } from './components/sistema/login/login.component';
import { rotaguardGuard } from './guards/rotaguard.guard';
import { CadastrarListComponent } from './components/sistema/cadastrar/cadastrar-list/cadastrar-list.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full'},
  { path: "login", component: LoginComponent },
  { 
    path: "admin", 
    component:IndexComponent,
    canActivate: [rotaguardGuard], 
    data: { roles: ['ADMIN'] }, 
    children:[
    { path: "cliente", component:ClienteListaComponent },
    { path: "pedidos", component:PedidoListaComponent },
    { path: "produtos", component:ProdutosListaComponent },
    { path: "sabores", component:SaboresListComponent },
    {  path: "cadastrar", component:CadastrarListComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
