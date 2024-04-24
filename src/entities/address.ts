export class Address {
    public static clone(a: Address):Address{
        return new Address(a.userId, a.street, a.number, a.postalCode, a.country, a.id);
    }

    constructor(
        public userId: number,
        public street: string,
        public number: number,
        public postalCode: string,
        public country: string,
        public id?: number,
    ){}

    public toString():string {
        return `id: ${this.id}, userId: ${this.userId}, street: ${this.street}, number: ${this.number}, postalCode: ${this.postalCode}, country: ${this.country}`;

    }
}