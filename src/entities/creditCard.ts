export class CreditCard {
    public static clone(c: CreditCard):CreditCard{
        return new CreditCard(c.id, c.userId, c.number, c.cvv);
    }

    constructor(
        public id: number,
        public userId: number,
        public number: string,
        public cvv: number

    ){}

    public toString():string {
        return `id: ${this.id}, userId: ${this.userId}, number: ${this.number}, cvv: ${this.cvv}`;

    }
}