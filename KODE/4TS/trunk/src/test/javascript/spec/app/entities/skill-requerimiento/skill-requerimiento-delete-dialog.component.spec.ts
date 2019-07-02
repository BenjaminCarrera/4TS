/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { SkillRequerimientoDeleteDialogComponent } from 'app/entities/skill-requerimiento/skill-requerimiento-delete-dialog.component';
import { SkillRequerimientoService } from 'app/entities/skill-requerimiento/skill-requerimiento.service';

describe('Component Tests', () => {
  describe('SkillRequerimiento Management Delete Component', () => {
    let comp: SkillRequerimientoDeleteDialogComponent;
    let fixture: ComponentFixture<SkillRequerimientoDeleteDialogComponent>;
    let service: SkillRequerimientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillRequerimientoDeleteDialogComponent]
      })
        .overrideTemplate(SkillRequerimientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillRequerimientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillRequerimientoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
