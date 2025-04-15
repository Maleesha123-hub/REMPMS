class Status {
  static statuses = {
    '-1': { label: 'Select a status', value: '-1' },
    Active: { label: 'Active', value: 'Active' },
    In_active: { label: 'In-active', value: 'In_active' },
  }

  static getStatus(value) {
    return this.statuses[value] || null
  }

  static getAllStatuses() {
    return Object.values(this.statuses)
  }
}

export default Status
