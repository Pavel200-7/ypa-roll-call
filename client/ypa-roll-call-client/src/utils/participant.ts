// utils/participant.ts

const PALETTE = [
  '#e57373', '#f06292', '#ba68c8', '#9575cd',
  '#7986cb', '#64b5f6', '#4fc3f7', '#4dd0e1',
  '#4db6ac', '#81c784', '#aed581', '#dce775',
  '#ffd54f', '#ffb74d', '#ff8a65', '#a1887f',
]

export function colorFromIdentity(identity: string): string|undefined {
  let hash = 0
  for (let i = 0; i < identity.length; i++) {
    hash = (hash * 31 + identity.charCodeAt(i)) | 0
  }
  return PALETTE[Math.abs(hash) % PALETTE.length]
}

export function initialsFromIdentity(identity: string): string {
  if (!identity) return '?'
  const parts = identity.split(/[._-]/)
  if (parts.length >= 2 && parts[0] && parts[1]) {
    return (parts[0][0]! + parts[1][0]).toUpperCase()
  }
  return identity.substring(0, 2).toUpperCase()
}