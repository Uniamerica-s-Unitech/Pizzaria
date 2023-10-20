import { Cliente } from "./cliente";

export class Endereco {
    id!: number;
    bairro!: string;
    rua!: string;
    numero!: number;
    clienteId!: Cliente;
}
