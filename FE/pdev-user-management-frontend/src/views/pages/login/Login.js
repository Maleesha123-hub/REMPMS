import React from 'react'
import styles from './login.module.css'
import '@fortawesome/fontawesome-free/css/all.min.css'
import { useNavigate } from 'react-router-dom'

const Login = () => {
  const navigate = useNavigate() // Initialize useNavigate

  const handleLogin = (e) => {
    e.preventDefault() // Prevent the default form submission
    navigate('/dashboard')
  }
  return (
    <div className={styles.container}>
      <div className="row">
        <div className="col-lg-3 col-md-2"></div>
        <div className={`col-lg-6 col-md-8 ${styles['login-box']}`}>
          <div className={`col-lg-12 ${styles['login-key']}`}>
            <i className="fa fa-key" aria-hidden="true"></i>
          </div>
          <div className={`col-lg-12 ${styles['login-title']}`}>PIXEL IAM PANEL</div>

          <div className={`col-lg-12 ${styles['login-form']}`}>
            <div className={`col-lg-12 ${styles['login-form']}`}>
              <form onSubmit={handleLogin}>
                <div className={styles['form-group']}>
                  <label className={styles['form-control-label']}>USERNAME</label>
                  <input type="text" className="form-control" />
                </div>
                <div className={styles['form-group']}>
                  <label className={styles['form-control-label']}>PASSWORD</label>
                  <input type="password" className="form-control" />
                </div>

                <div
                  className={`col-lg-12 ${styles['loginbttm']} text-end`}
                  style={{ paddingBottom: '30px' }}
                >
                  <button type="submit" className={`btn ${styles['btn-outline-primary']}`}>
                    LOGIN
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div className={`col-lg-3 col-md-2`}></div>
        </div>
      </div>
    </div>
  )
}

export default Login
