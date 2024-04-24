
export class User {
    public static clone(u: User):User{
        return new User(u.id, u.firstName, u.lastName, u.password, u.age, u.email);
    }

    constructor(
        public id: number,
        public firstName: string,
        public lastName: string,
        public password: string,
        public age: number,
        public email: string,
    ){}

    public toString():string {
        return `id: ${this.id}, firstName: ${this.firstName}, lastName: ${this.lastName}, email: ${this.email}, password: ${this.password}, age: ${this.age}`;

    }
}