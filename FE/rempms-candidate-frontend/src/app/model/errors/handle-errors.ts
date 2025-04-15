export interface HandleErrors {
  error: {
    // For default errors like 500 | internal server error etc.
    error: string;
    status: number;
    path: string;
    timestamp: string;
    // For custome erros
    message: string;
    httpStatus: string;
    localDateTime: string;
  };
}
