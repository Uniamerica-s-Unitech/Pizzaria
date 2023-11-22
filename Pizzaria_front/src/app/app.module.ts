import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteListaComponent } from './components/cliente/cliente-lista/cliente-lista.component';
import { ClienteDetalhesComponent } from './components/cliente/cliente-detalhes/cliente-detalhes.component';
import { PedidoDetalhesComponent } from './components/pedido/pedido-detalhes/pedido-detalhes.component';
import { PedidoListaComponent } from './components/pedido/pedido-lista/pedido-lista.component';
import { EnderecoDetalhesComponent } from './components/cliente/endereco-detalhes/endereco-detalhes.component';
import { ProdutosListaComponent } from './components/produtos/produtos-lista/produtos-lista.component';
import { ProdutosDetalhesComponent } from './components/produtos/produtos-detalhes/produtos-detalhes.component';
import { SaboresListComponent } from './components/sabores/sabores-list/sabores-list.component';
import { SaboresDetalhesComponent } from './components/sabores/sabores-detalhes/sabores-detalhes.component';
import { IndexComponent } from './components/layout/index/index.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { CategoriaDetalhesComponent } from './components/produtos/categoria-detalhes/categoria-detalhes.component';
import { SelecionarProdutosComponent } from './components/pedido/selecionar-produtos/selecionar-produtos.component';
import { HistoricoPedidosComponent } from './components/pedido/historico-pedidos/historico-pedidos.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    ClienteListaComponent,
    ClienteDetalhesComponent,
    PedidoDetalhesComponent,
    PedidoListaComponent,
    EnderecoDetalhesComponent,
    ProdutosListaComponent,
    ProdutosDetalhesComponent,
    SaboresListComponent,
    SaboresDetalhesComponent,
    IndexComponent,
    HeaderComponent,
    FooterComponent,
    CategoriaDetalhesComponent,
    SelecionarProdutosComponent,
    HistoricoPedidosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
