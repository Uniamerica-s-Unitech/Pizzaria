import { Categoria } from "./categoria";

export class Produto {
    id!: number;
    nome!:string;
    categoriaId!: Categoria;
    valor!:number;
    tamanho!: string;
    temSabores!: boolean;
}
