import { ref, type Ref } from 'vue';
import {
    Track,
} from 'livekit-client';
import { ParticipantInfo } from '@/types/room/ParticipantInfo';

export class ParticipantsInfo {
    private participants: Map<string, ParticipantInfo>;
    private _changeTracker: Ref<number>;

    constructor() {
        this.participants = new Map<string, ParticipantInfo>();
        this._changeTracker = ref(0);
    }

    private emitChange(): void {
        this._changeTracker.value++;
    }

    private ensureReactive(): void {
        this._changeTracker.value;
    }

    clear(): void {
        this.participants.clear();
        this._changeTracker.value = 0;
    }

    addNewParticipant(
        participantIdentity: string,
        isLocal: boolean = false,
        metadata: string | null
    ): void {
        console.log(`Добавление нового участника: ${participantIdentity}`);
        if (!this.hasParticipant(participantIdentity)) {
            this.participants.set(participantIdentity, new ParticipantInfo(participantIdentity, isLocal, metadata));
            console.log(`Добавлен новый участник: ${participantIdentity}`);
            this.emitChange();
        }
    }

    addExistingParticipant(
        participantIdentity: string,
        videoTrack: Track | null = null,
        audioTrack: Track | null = null,
        isLocal: boolean = false,
        metadata: string | null
    ): void {
        console.log(`Добавление существующего участника: ${participantIdentity}`);
        if (!this.hasParticipant(participantIdentity)) {
            this.participants.set(participantIdentity, new ParticipantInfo(participantIdentity, isLocal, metadata));
            const participant = this.participants.get(participantIdentity);
            if (videoTrack) {
                participant!.addTrack(videoTrack);
            }
            if (audioTrack) {
                participant!.addTrack(audioTrack);
            }
            console.log(`Добавлен существующий участник: ${participantIdentity}`);
            this.emitChange();
        }
    }

    deleteParticipant(participantIdentity: string): void {
        console.log(`Удаление участника: ${participantIdentity}`);
        if (this.hasParticipant(participantIdentity)) {
            this.participants.delete(participantIdentity);
            console.log(`Удален участник: ${participantIdentity}`);
            this.emitChange();
        }
    }

    get(participantIdentity: string): ParticipantInfo | undefined {
        return this.participants.get(participantIdentity);
    }

    getLocal(): ParticipantInfo | undefined {
        for (const [identity, info] of this.getAll()) {
            if (info.isLocal() == true) {
                return info;
            }
        }
        return;
    }

    getRemote(): Map<string, ParticipantInfo> {
        const result = new Map<string, ParticipantInfo>();
        for (const [identity, info] of this.getAll()) {
            if (info.isLocal() === false) {
                result.set(identity, info);
            }
        }
        return result;
    }

    getAll(): Map<string, ParticipantInfo> {
        return new Map(this.participants);
    }

    get reactive(): {
        getAll: () => Map<string, ParticipantInfo>;
        getLocal: () => ParticipantInfo | undefined;
        getRemote: () => Map<string, ParticipantInfo>;
        getParticipant: (identity: string) => ParticipantInfo | undefined;
    } {
        this.ensureReactive();

        return {
            getAll: this.getAll.bind(this),
            getLocal: this.getLocal.bind(this),
            getRemote: this.getRemote.bind(this),
            getParticipant: this.get.bind(this),
        };
    }

    hasParticipant(participantIdentity: string): boolean {
        return this.participants.has(participantIdentity);
    }

    resetSpeakers(): void {
        for (const [identity, info] of this.getAll()) {
            info.setIsSpeaking(false);
        }
    }

    get size(): number {
        return this.participants.size;
    }
}