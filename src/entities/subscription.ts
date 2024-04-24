export class Subscription {
    public static clone(s: Subscription):Subscription{
        return new Subscription(s.userId, s.startDate, s.endDate, s.id);
    }

    constructor(
        public userId: number,
        public startDate: Date,
        public endDate: Date,
        public id?: number,
    ){}

    public toString():string {
        return `id: ${this.id}, userId: ${this.userId}, startDate: ${this.startDate}, endDate: ${this.endDate}`;

    }
}