export class ParticipantIdentity {
    identity: string;

    constructor(identity: string) {
        this.identity = identity;
    }

    getShortIdentity(): string {
        if (!this.identity) return '?';
        const parts = this.identity.split(/[._-]/);
        if (parts.length >= 2 && parts[0]?.length && parts[1]?.length) {
            return (parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        }
        return this.identity.substring(0, 2).toUpperCase();
    }

}