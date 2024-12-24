export const DateUtils =  {
    formatDate(date: string | Date) {
        const parsedDate = new Date(date); 
        return parsedDate.toLocaleDateString();  
     },
    isDateNDaysFromNow (dateFrom: Date | string, days: number){
        const dateFromDate = typeof dateFrom === 'string' ? new Date(dateFrom) : dateFrom;
        const now = new Date();
        const targetDate = new Date(now);
        targetDate.setDate(now.getDate() + days);
            return dateFromDate <= targetDate;
      },
     convertToUTC (date: Date) {
      const utcIsoString = date.toISOString();
      return new Date(utcIsoString);
      },

      formatDateTime(date: string | Date) {
        const parsedDate = new Date(date); 
        return parsedDate.toLocaleString();  
     }
};
