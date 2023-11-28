import { Cliente } from "./cliente";
import { Endereco } from "./endereco";
import { PedidoProduto } from "./pedido-produto";

export class Pedido {
  id!: number;
  clienteId!: Cliente;
  enderecoId!: Endereco;
  produtos!: PedidoProduto[];
  solicitacao!: Date;
  finalizacao!: Date;
  valorTotal!:number;
}